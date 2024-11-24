import { getProductBySlug } from '../api/fetchProducts'
import { setLoading } from '../store/features/common'
import store from '../store/store'

export const loadProductBySlug = async ({ params }) => {
    // const product = content?.products?.find((product) => product?.id.toString() === params?.productId?.toString());
    // return {product}
    try {
        store.dispatch(setLoading(true))
        const product = await getProductBySlug(params?.slug)
        store.dispatch(setLoading(false))
        return { product }
    } catch (err) {

    }

}