import {createSlice} from "@reduxjs/toolkit"

const initialState = {
    cart: JSON.parse(localStorage.getItem('cart')) || []
}

const cartSlice = createSlice({
    name: 'cartState',
    initialState: initialState,
    reducers: {
        addToCart: (state, action) => {
            const existingItem = state.cart.find(
                (item) =>
                    item.productId === action.payload.productId &&
                    item.variant.id === action.payload.variant.id
            );

            if (existingItem) {
                // Increment the quantity if the item exists
                existingItem.quantity += action.payload.quantity;
                existingItem.subTotal += action.payload.subTotal;
            } else {
                // Add the item to the cart if it doesn't exist
                state.cart.push(action.payload);
            }
        },
        removeFromCart: (state, action) => {
            state.cart = state.cart.filter(
                (item) =>
                    item.productId !== action.payload.productId ||
                    item.variant.id !== action.payload.variantId
            );
        },
        updateQuantity: (state, action) => {
            const item = state.cart.find(
                (item) => item.variant.id === action.payload.variant_id
            );

            if (item) {
                item.quantity = action.payload.quantity;
                item.subTotal = item.price * action.payload.quantity;
            }
        },
        deleteCart: (state, action) => {
            return {
                ...state,
                cart: []
            }
        }
    }
})

export const {addToCart,
    removeFromCart,
    updateQuantity,
    deleteCart} = cartSlice?.actions;

// export const countCartItems = (state) => state?.cartState?.cart?.length;
export const countCartItems = (state) =>
    state?.cartState?.cart?.reduce((total, item) => total + item.quantity, 0) || 0;
export const selectCartItems = (state) => state?.cartState?.cart ?? []
export default cartSlice.reducer;