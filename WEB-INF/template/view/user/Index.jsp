<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/include/Header.jsp" %>

<section class="status">
    <nav class="status-nav">
        <ul>
            <li><a href="#">今週</a></li>
            <li><a href="#">1週前</a></li>
            <li><a href="#">2週前</a></li>
            <li><a href="#">3週前</a></li>
        </ul>
    </nav>
    <div class="status-contents">
        <div class="calendar">
            <span class="today">5/24 - 5/27</span>
            <span class="title">自炊履歴</span>
            <ul>
                <li class="cooked">SUN</li>
                <li>MON</li>
                <li class="cooked">TUE</li>
                <li class="cooked">WED</li>
                <li>THU</li>
                <li class="cooked">FRI</li>
                <li>SAT</li>
            </ul>
        </div>
        <div class="status-counter">
            <dl><dt>自炊回数</dt><dd id="total-cooked">--回</dd></dl>
            <dl><dt>連続自炊日数</dt><dd id="consecutively-cooked">--日</dd></dl>
            <dl><dt>栄養バランス</dt><dd id="nutrients-balance">--%</dd></dl>
        </div>
        <canvas class="pie-graph"></canvas>
    </div>
</section>

<section class="tips">
    <h2>今日のTips</h2>
    <p>　<c:out value="${ tips }" /></p>
</section>

<section class="recommends-wrapper">
    <h2>あなたに合わせたおすすめレシピ</h2>
    <div class="recommends">
        <c:forEach var="recipe" items="${ recommendedRecipes }" varStatus="status">
            <% RecipeVo recipe = (RecipeVo) pageContext.getAttribute("recipe"); %>
            <c:if test="${0 == status.index}">
                <a class="recipe recipe-main" href="<% v.getPath("/user/recipe/Detail?recipe_id=" + recipe.getRecipeId()); %>">
                    <img alt="料理名" src="<% v.getPath("/asset/img/sample_food.jpg"); %>">
                    <h3><c:out value="${ recipe.recipeName }" /></h3>
                </a>
            </c:if>
            <c:if test="${0 < status.index}">
                <a class="recipe" href="<% v.getPath("/user/recipe/Detail?recipe_id=" + recipe.getRecipeId()); %>">
                    <img alt="料理名" src="<% v.getPath("/asset/img/sample_food.jpg"); %>">
                    <h3><c:out value="${ recipe.recipeName }" /></h3>
                </a>
            </c:if>
        </c:forEach>
    </div>
    <p>あなたの過去の調理履歴を参考に、体に不足しているであろう食材を中心としたレシピをピックアップしています。</p>
    <a href="<% v.getPath("/user/recipe/Search"); %>" class="btn btn-primary">もっと見る</a>
</section>

<section class="materials-wrapper">
    <h2>体に不足している食材から選ぶ</h2>
    <h3>食材名から検索</h3>
    <div class="search">
        <form method="POST" action="<% v.getPath("/user/recipe/Search"); %>">
            <input class="input-middle" type="search" placeholder="使いたい食材名を入力" required>
            <input class="btn btn-primary btn-small" type="submit" value="検索">
        </form>
    </div>

    <h3>食材分類から選ぶ</h3>
    <div class="materials">
        <div id="milk"    class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">乳製品</a></div>
        <div id="egg"     class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">卵</a></div>
        <div id="meat"    class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">魚・肉類</a></div>
        <div id="bean"    class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">豆類</a></div>
        <div id="veg"     class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">野菜</a></div>
        <div id="fruit"   class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">果物</a></div>
        <div id="mineral" class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">海藻・茸</a></div>
        <div id="crop"    class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">穀物</a></div>
        <div id="potato"  class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">イモ類</a></div>
        <div id="fat"     class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">油分</a></div>
        <div id="sugar"   class="material"><a href="<% v.getPath("/user/recipe/Search"); %>">糖分</a></div>
    </div>
    <p>最近不足している食材が強調表示されています。</p>
</section>

<%@ include file="/WEB-INF/template/include/Footer.jsp" %>