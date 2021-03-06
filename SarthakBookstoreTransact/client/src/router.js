import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import Category from "./views/Category.vue";
import Cart from "@/views/Cart";
import Checkout from "@/views/Checkout";
import Confirmation from "@/views/Confirmation";
import NotFound from "@/views/NotFound";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/home",
      name: "home",
      component: Home,
      alias: ["/", "/index.html"],
    },
    {
      path: "/category/:name",
      name: "category",
      component: Category,
      alias: ["category/My Category"],
      props: true,
    },
    {
      path: "/category",
      redirect: "/category/Kids",
    },
    {
      path: "/cart",
      name: "cart",
      component: Cart,
    },
    {
      path: "/checkout",
      name: "checkout",
      component: Checkout,
    },
    {
      path: "/confirmation",
      name: "confirmation",
      component: Confirmation,
    },
    {
      path: "*",
      component: NotFound,
    },
  ],
});
