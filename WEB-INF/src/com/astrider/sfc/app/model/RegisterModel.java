package com.astrider.sfc.app.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.model.dao.UserDao;
import com.astrider.sfc.app.model.dao.UserStatsDao;
import com.astrider.sfc.app.model.vo.db.UserStatsVo;
import com.astrider.sfc.app.model.vo.db.UserVo;
import com.astrider.sfc.app.model.vo.form.ConfirmEmailVo;
import com.astrider.sfc.app.model.vo.form.RegisterFormVo;
import com.astrider.sfc.lib.helper.AuthUtils;
import com.astrider.sfc.lib.helper.Mailer;
import com.astrider.sfc.lib.helper.Mapper;
import com.astrider.sfc.lib.helper.StringUtils;
import com.astrider.sfc.lib.helper.Validator;
import com.astrider.sfc.lib.model.BaseModel;

/*
 * ユーザー登録関連model
 */
public class RegisterModel extends BaseModel {
    /*
     * ユーザー新規登録
     */
    public boolean registerUser(HttpServletRequest request) {
        // フォーム情報取得
        Mapper<RegisterFormVo> m = new Mapper<RegisterFormVo>();
        RegisterFormVo registerForm = (RegisterFormVo) m.fromHttpRequest(request);

        // 汎用バリデーション
        Validator<RegisterFormVo> validator = new Validator<RegisterFormVo>(registerForm);
        if (!validator.valid()) {
            flashMessage.addMessage(validator.getFlashMessage());
            return false;
        }

        // パスワード一致
        if (!registerForm.getPassword().equals(registerForm.getPasswordConfirm())) {
            flashMessage.addMessage("パスワードが一致しません");
            return false;
        }

        // DBにユーザーを追加
        UserVo user = new UserVo(registerForm);
        user.setAuthToken(AuthUtils.encrypt(registerForm.getPassword()));
        user.setEmailToken(StringUtils.getEmailToken(registerForm.getEmail()));
        UserDao userDao = new UserDao();
        boolean succeed = userDao.insert(user);
        userDao.close();
        if (!succeed) {
            flashMessage.addMessage("仮登録に失敗しました。お客様のメールアドレスは既に登録されている可能性があります。");
            return false;
        }

        // 仮登録メール　本文作成
        String to = user.getEmail();
        String subject = "仮登録メール";
        String body = "";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(user.getUserName() + "様\n\n");
            sb.append("この度はsanteに仮登録いただきありがとうございます。\n");
            sb.append("以下のURLをクリックすることによって本登録が完了いたします。\n");
            sb.append("http://" + request.getServerName() + request.getContextPath() + "/register/ConfirmEmail");
            sb.append("?email=" + URLEncoder.encode(user.getEmail(), "UTF-8"));
            sb.append("&token=" + URLEncoder.encode(user.getEmailToken(), "UTF-8"));
            body = sb.toString();
        } catch (UnsupportedEncodingException e) {
            flashMessage.addMessage("仮登録完了のメール本文の作成に失敗しました");
            return false;
        }

        // 仮登録メール送信
        Mailer mailer = new Mailer(to, subject, body);
        if (!mailer.send()) {
            flashMessage.addMessage("仮登録完了のメール送信に失敗しました");
            return false;
        }

        return true;
    }

    /*
     * メールアドレス確認
     */
    public boolean confirmMail(HttpServletRequest request) {
        // 引数取得
        Mapper<ConfirmEmailVo> mapper = new Mapper<ConfirmEmailVo>();
        ConfirmEmailVo vo = mapper.fromHttpRequest(request);

        // 引数の整合性確認
        Validator<ConfirmEmailVo> validator = new Validator<ConfirmEmailVo>(vo);
        if (!validator.valid()) {
            System.out.println("invalid request");
            flashMessage.addMessage(validator.getFlashMessage());
            return false;
        }

        // 認証トークン確認
        UserDao userDao = new UserDao();
        UserVo user = userDao.selectByEmailToken(vo);
        if (user == null) {
            flashMessage.addMessage("トークンが確認できませんでした。");
            userDao.close();
            return false;
        }

        // ユーザーを認証済みに
        user.setAvailable(true);
        user.setConfirmed(true);
        if (!userDao.update(user)) {
            flashMessage.addMessage("不明なエラー");
            userDao.close();
            return false;
        }
        userDao.close();

        // userStatsテーブルにレコードを追加
        UserStatsDao userStatsDao = new UserStatsDao();
        UserStatsVo userStats = new UserStatsVo();
        userStats.setUserId(user.getUserId());
        userStatsDao.insert(userStats);
        userStatsDao.close();

        // sessionをログイン済みに
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        return true;
    }
}
