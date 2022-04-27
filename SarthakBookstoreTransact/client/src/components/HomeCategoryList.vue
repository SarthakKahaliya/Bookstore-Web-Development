<template>
  <ul>
    <template v-for="category in $store.state.categories">
      <li
        :key="category.categoryId"
        v-if="$route.params.name === category.name"
        class="button unselected-category-button"
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
          <img
            v-bind:src="
              require('@/assets/images/categories/' +
                categoryImageFileName(category))
            "
            :alt="category.name"
            width="180px"
            height="180px"
          />
          {{ category.name }}
        </router-link>
      </li>
    </template>
    <!--    <router-link to="../category/Kids" tag="li">
      <img
        src="@/assets/images/categories/kids.jpg"
        alt="kids category"
        width="180px"
        height="180px"
      />
      <div>Kids</div>
    </router-link>
    <router-link to="../category/Math" tag="li">
      <img
        src="@/assets/images/categories/math.jpg"
        alt="math category"
        width="180px"
        height="180px"
      />
      <div>Math</div>
    </router-link>
    <router-link to="../category/Physics" tag="li">
      <img
        src="@/assets/images/categories/physics.jpg"
        alt="physics category"
        width="180px"
        height="180px"
      />
      <div>Physics</div>
    </router-link>
    <router-link to="../category/Chemistry" tag="li">
      <img
        src="@/assets/images/categories/chemistry.jpg"
        alt="chemistry category"
        width="180px"
        height="180px"
      />
      <div>Chemistry</div>
    </router-link>-->
  </ul>
</template>

<script>
export default {
  name: "HomeCategoryList",

  methods: {
    sendCategory(name) {
      this.$emit("clicked", name);
    },

    categoryImageFileName: function (category) {
      let name = category.name.toLowerCase();
      name = name.replace(/ /g, "-");
      name = name.replace(/\n/g, "");
      console.log(name);
      return `${name}.jpg`;
    },
  },
};
</script>

<style scoped>
ul {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 1em;
}

li {
  text-align: center;
  cursor: pointer;
}

li div {
  padding: 0.5em 0;
  background: rgba(0, 0, 0, 0.5); /* last # is percent opacity */
  color: white;
  transform: translateY(-2.25em);
  margin-bottom: -2em;
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
  border: solid var(--primary-color);
  border-radius: 20px;
  transform: scale(100%);
  transition-duration: 400ms;
}
a.button.unselected-category-button {
  padding: 0px;
}
img {
  border-radius: 15px 15px 0px 0px;
}

.button.selected-category-button:hover,
.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  background-color: var(--primary-color);
  color: white;
  transform: scale(110%);
}
</style>
