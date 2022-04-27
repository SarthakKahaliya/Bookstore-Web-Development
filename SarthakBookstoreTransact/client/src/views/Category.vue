<template>
  <div>
    <category-nav></category-nav>
    <category-book-list> </category-book-list>
  </div>
</template>

<script>
import CategoryNav from "@/components/CategoryNav";
import CategoryBookList from "@/components/CategoryBookList";
import router from "@/router";

export default {
  name: "category",
  components: {
    CategoryNav,
    CategoryBookList,
  },

  created: function () {
    this.$store.dispatch("selectCategory", this.$route.params.name);
    var name = this.$store.state.selectedCategoryName;
    this.$store.dispatch("fetchSelectedCategoryBooks", name).catch(function () {
      router.push("/404"); // '/404' triggers NotFound
    });
  },
};
</script>

<style scoped></style>
