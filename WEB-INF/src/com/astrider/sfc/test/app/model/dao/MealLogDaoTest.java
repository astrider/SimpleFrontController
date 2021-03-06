package com.astrider.sfc.test.app.model.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.astrider.sfc.app.model.vo.db.MealLogVo;
import com.astrider.sfc.app.model.vo.db.RecipeNutAmountsVo;
import com.astrider.sfc.app.model.vo.db.RecipeVo;
import com.astrider.sfc.app.model.vo.db.UserVo;

public class MealLogDaoTest extends DaoTestBase {

    @Test
    public void レシピIDから食事ログを生成() {
        UserVo user = userDao.selectByUserId(1);
        RecipeVo recipe = recipeDao.selectByRecipeId(1);
        assertTrue(mealLogDao.insertFromRecipeNutAmounts(user.getUserId(), recipe.getRecipeId()));
    }

    @Test
    public void 挿入された食事ログが取得できるか確認() {
        int userId = 1;
        int recipeId = 1;
        RecipeNutAmountsVo recipeNut = recipeNutDao.selectById(recipeId);
        assertTrue(recipeNut != null);
        assertTrue(mealLogDao.insertFromRecipeNutAmounts(userId, recipeId));
        MealLogVo mealLog = mealLogDao.selectNewestByUserId(userId);
        assertTrue(mealLog != null);
        compareRecipeNutWithMealLog(recipeNut, mealLog);
    }

    @Test
    public void 今週の食事回数を取得() {
        int userId = 1;
        int recipeId = 1;
        int counter = 5;
        for (int i = 0; i < counter; i++) {
            assertTrue(mealLogDao.insertFromRecipeNutAmounts(userId, recipeId));
        }
        assertTrue(mealLogDao.countMealOfThisWeek(userId) == counter);
    }

    private void compareRecipeNutWithMealLog(RecipeNutAmountsVo recipeNut, MealLogVo mealLog) {
        assertTrue(mealLog.getBean() == recipeNut.getBean());
        assertTrue(mealLog.getCrop() == recipeNut.getCrop());
        assertTrue(mealLog.getEgg() == recipeNut.getEgg());
        assertTrue(mealLog.getFat() == recipeNut.getFat());
        assertTrue(mealLog.getFruit() == recipeNut.getFruit());
        assertTrue(mealLog.getMeat() == recipeNut.getMeat());
        assertTrue(mealLog.getMilk() == recipeNut.getMilk());
        assertTrue(mealLog.getMineral() == recipeNut.getMineral());
        assertTrue(mealLog.getPotato() == recipeNut.getPotato());
        assertTrue(mealLog.getSuguar() == recipeNut.getSuguar());
        assertTrue(mealLog.getTotalBalance() == recipeNut.getTotalBalance());
        assertTrue(mealLog.getVegetable() == recipeNut.getVegetable());
    }
}
