<template>
  <div class="main-menu-container">
    <button
      class="button icon-button main-menu-button"
      v-on:click="toggleMainMenu"
    >
      <i class="fas fa-bars"></i>
    </button>

    <ul class="main-menu-panel" :class="{ 'main-menu-open': mainMenuOpen }">
      <template v-for="category in $store.state.categories">
        <li :key="category.categoryId" @click="sendCategory(category.name)">
          <router-link
            :to="{ name: 'category', params: { name: category.name } }"
            class="main-menu-item"
          >
            {{ category.name }}
          </router-link>
        </li>
      </template>
      <li class="main-menu-item">Account</li>
      <li class="main-menu-item">Login</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "HeaderDropdown",

  data: function () {
    return {
      mainMenuOpen: false,
    };
  },

  methods: {
    toggleMainMenu() {
      this.mainMenuOpen = !this.mainMenuOpen;
    },

    sendCategory(name) {
      this.$store.dispatch("selectCategory", name);
      this.$store.dispatch("fetchSelectedCategoryBooks", name);
    },
  },
};
</script>

<style scoped>
.main-menu-container {
  position: relative;
}

.button.icon-button.main-menu-button {
  border: solid;
  border-radius: 90px;
}

.button.icon-button.main-menu-button:hover {
  background: var(--primary-color-dark);
  color: white;
}

.main-menu-panel {
  background-color: white;
  color: var(--primary-color-dark);

  position: absolute;
  right: 0;
  transform: translateY(-400%);
  transition-duration: 500ms;
  transition-timing-function: ease-in-out;
  margin-top: 10px;
  padding: 0.25em;
  border-radius: 5px;
  box-shadow: 0 0 10px 0 #0008;
}

.main-menu-panel.main-menu-open {
  transform: translateY(0%);
}

.main-menu-item {
  display: inline-block;
  width: 100%;
  text-decoration: none;
  padding: 0.5em 1em 0.5em 1em;
  cursor: pointer;
}

.main-menu-item:hover {
  background: var(--primary-color);
  color: white;
  border-radius: 5px;
}
</style>
