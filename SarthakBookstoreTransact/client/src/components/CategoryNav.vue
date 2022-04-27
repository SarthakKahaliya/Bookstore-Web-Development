<template>
  <nav class="category-nav">
    <ul class="category-page-buttons">
      <template v-for="category in $store.state.categories">
        <li
          :key="category.categoryId"
          v-if="$route.params.name === category.name"
          class="button selected-category-button"
          @click="sendCategory(category.name)"
        >
          {{ category.name }}
        </li>
        <li
          :key="category.categoryId"
          @click="sendCategory(category.name)"
          v-else
        >
          <router-link
            :to="{ name: 'category', params: { name: category.name } }"
            class="button unselected-category-button"
          >
            {{ category.name }}
          </router-link>
        </li>
      </template>
      <!--      <li>
        <router-link
          to="/category/kids"
          tag="li"
          class="button selected-category-button" 
        >
          Kids
        </router-link>

        <router-link
          to="/category/math"
          tag="li"
          class="button unselected-category-button"
        >
          Math
        </router-link>
        <router-link
          to="/category/physics"
          tag="li"
          class="button unselected-category-button"
        >
          Physics
        </router-link>
        <router-link
          to="/category/chemistry"
          tag="li"
          class="button unselected-category-button"
        >
          Chemistry
        </router-link>
      </li>-->
    </ul>
  </nav>
</template>

<script>
export default {
  name: "CategoryNav",

  methods: {
    sendCategory(name) {
      this.$store.dispatch("selectCategory", name);
      this.$store.dispatch("fetchSelectedCategoryBooks", name);
    },
  },
};
</script>
<style scoped>
.category-page-buttons {
  display: flex;
  flex-direction: row;
  justify-content: center;
  flex-basis: 100%;
  text-align: center;
  background: #fdf5e6;
  border-radius: 90px 90px 0px 0px;
}

.button.selected-category-button {
  background-color: var(--primary-color-dark);
  color: white;
  border-radius: 20px 20px 0px 0px;
  border: solid var(--primary-color-dark);
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: #fdf5e6;
  border: solid var(--primary-color-dark);
  border-radius: 20px 20px 0px 0px;
}

.button.selected-category-button:hover,
.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  background-color: var(--primary-color);
  color: white;
}
</style>
