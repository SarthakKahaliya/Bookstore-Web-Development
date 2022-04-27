<template>
  <div>
    <div class="headline">
      <section class="welcome-text">
        <h2>
          Welcome to Book Buggz. The Greatest Gift is the Passion for Reading.
        </h2>
        <p></p>
      </section>
    </div>
    <div class="home-page" :style="cssProps">
      <section class="category-images container">
        <home-category-list></home-category-list>

        <router-link to="../category/Kids"
          ><button class="shop-now-button">Start Shopping</button></router-link
        >
      </section>
    </div>
  </div>
</template>

<script>
import HomeCategoryList from "@/components/HomeCategoryList";
export default {
  name: "Home",
  components: { HomeCategoryList },
  data() {
    return {
      cssProps: {
        backgroundImage: `url(${require("@/assets/images/site/book_stack.png")})`,
      },
    };
  },
};
</script>

<style scoped>
.home-page {
  display: block;
  background-image: linear-gradient(
    rgba(255, 255, 255, 0.35),
    rgba(255, 255, 255, 0.35)
  );
  background-repeat: no-repeat;

  padding-top: 5em;
  padding-bottom: 5em;
  border-radius: 0 0 30px 30px;
}
/*section.category-images.container {
  background-image: linear-gradient(
    rgba(255, 255, 255, 0.35),
    rgba(255, 255, 255, 0.35)
  );
  background-repeat: no-repeat;

  padding-top: 5em;
  padding-bottom: 5em;
  border-radius: 0 0 90px 90px;
}*/

section.welcome-text {
  text-align: center;
  padding-top: 1em;
  padding-bottom: 1em;
  color: var(--primary-color);
  background: #fdf5e6;
  border-radius: 90px 90px 0px 0px;
}

.category-images {
  display: grid;
  justify-content: center;
  align-items: center;
  flex-flow: row;
  flex-wrap: nowrap;
  align-content: space-around;
  justify-items: center;
}

.shop-now-button {
  text-align: center;
  margin-top: 4em;
  justify-content: center;
  width: 216px;
  height: 60px;
  background: var(--primary-color);
  color: white;
  border: none;
  border-radius: 90px;
  cursor: pointer;
}

.shop-now-button:hover,
.shop-now-button:active {
  background: var(--primary-color-dark);
}

.shop-now-button a {
  color: white;
  text-decoration: none;
}

.category-image-items a {
  text-align: center;
  cursor: pointer;
  filter: none;
}

.category-image-items div {
  padding: 0.5em 0;
  background: rgba(0, 0, 0, 0.5); /* last # is percent opacity */
  color: white;
  transform: translateY(-2.25em);
  margin-bottom: -2em;
}
</style>
