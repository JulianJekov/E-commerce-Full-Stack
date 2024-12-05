import { addToCart, deleteCart, removeFromCart, updateQuantity } from "../features/cart"


export const addItemToCartAction = (productItem) => {
    return (dispatch, getState) => {
        const state = getState();
        const existingCart = state.cartItems?.cart || []; // Ensure cart is always an array

        // Validate payload
        if (!productItem || !productItem.productId || !productItem.variant) {
            console.error("Invalid product item:", productItem);
            return; // Abort if payload is invalid
        }

        // Find if the item already exists in the cart
        const existingItem = existingCart.find(
            (item) =>
                item.productId === productItem.productId &&
                item.variant?.id === productItem.variant?.id
        );

        const existingQuantity = existingItem?.quantity || 0;
        const productQuantity = productItem.quantity || 1; // Default to 1

        if (existingItem) {
            // If the item exists, update the quantity
            dispatch(updateQuantity({
                variant_id: productItem.variant.id,
                quantity: existingQuantity + productQuantity,
            }));
        } else {
            // If the item doesn't exist, add it to the cart
            dispatch(addToCart({
                ...productItem,
                quantity: productQuantity,
            }));
        }

        updateLocalStorage(getState);
    };
};

export const updateItemToCartAction = (productItem) =>{
    return (dispatch,state) =>{
        dispatch(updateQuantity({
            variant_id: productItem?.variant_id,
            quantity: productItem?.quantity
        }))
        updateLocalStorage(state);

    }
}

export const deleteItemFromCartAction = (payload)=>{
    return (dispatch,state)=>{
        dispatch(removeFromCart(payload));
        updateLocalStorage(state);
    }
}

const updateLocalStorage = (state)=>{
    const {cartState} = state();
    localStorage.setItem('cart',JSON.stringify(cartState?.cart))
}

export const clearCart = ()=>{
    return (dispatch,state) =>{
        dispatch(deleteCart());
        localStorage.removeItem('cart');
    }
}