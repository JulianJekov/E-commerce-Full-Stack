import { createBrowserRouter } from "react-router-dom";
import Shop from "./Shop";
import ProductListPage from "./components/pages/ProductListPage/ProductListPage";
import ShopApplicationWrapper from "./components/pages/ShopApplicationWrapper";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <ShopApplicationWrapper />,
        children:[
            {
                path:"/",
                element:<Shop />
            },
            {
                path:"/womens",
                element:<ProductListPage categoryType={'WOMEN'} />
            },
            {
                path:"/mens",
                element:<ProductListPage categoryType={'MEN'} />
            },
            {
                path:"/kids",
                element:<ProductListPage categoryType={'KIDS'} />
            }
        ]
    }
 
])