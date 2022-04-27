<template>
  <ul class="list" :style="cssProps">
    <category-book-list-item
      :book="$store.state.selectedCategoryBooks[0]"
    ></category-book-list-item>
    <category-book-list-item
      :book="$store.state.selectedCategoryBooks[1]"
    ></category-book-list-item>
    <category-book-list-item
      :book="$store.state.selectedCategoryBooks[2]"
    ></category-book-list-item>
    <category-book-list-item
      :book="$store.state.selectedCategoryBooks[3]"
    ></category-book-list-item>
  </ul>
</template>

<script>
import CategoryBookListItem from "./CategoryBookListItem";

export default {
  name: "CategoryBookList",
  components: {
    CategoryBookListItem,
  },
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
ul.list {
  background-image: linear-gradient(
    rgba(255, 255, 255, 0.35),
    rgba(255, 255, 255, 0.35)
  );
  display: flex;
  flex-wrap: wrap;
  padding: 1em 1em 2em 1em;
  gap: 1em;

  border-radius: 0 0 10px 10px;
}
</style>
