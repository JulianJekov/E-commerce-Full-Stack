import { createBrowserRouter } from "react-router-dom";
import Shop from "./Shop";
import ProductListPage from "./components/pages/ProductListPage/ProductListPage";
import ShopApplicationWrapper from "./components/pages/ShopApplicationWrapper";
import ProductDetails from "./components/pages/ProductDetailPage/ProductDetails";
import { loadProductBySlug } from "./routes/products";
import AuthenticationWrapper from "./components/pages/AuthenticationWrapper";
import Login from "./components/pages/Login/Login";
import Register from "./components/pages/Register/Register";
import OAuth2LoginCallback from "./components/pages/OAuth2LoginCallback";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <ShopApplicationWrapper />,
        children: [
            {
                path: "/",
                element: <Shop />
            },
            {
                path: "/women",
                element: <ProductListPage categoryType={'WOMEN'} />
            },
            {
                path: "/men",
                element: <ProductListPage categoryType={'MEN'} />
            },
            {
                path: "/kid",
                element: <ProductListPage categoryType={'KIDS'} />
            },
            {
                path: "/product/:slug",
                loader: loadProductBySlug,
                element: <ProductDetails />
            }
        ]
    },
    {
        path: "/v1/",
        element: <AuthenticationWrapper />,
        children:[
            {
                path: "login",
                element: <Login />

            },
            {
                path: "register",
                element: <Register />

            }
        ]
    },
    {
        path: "/oauth2/callback",
        element:<OAuth2LoginCallback />
    }

])