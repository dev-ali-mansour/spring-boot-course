export const formatPrice = (amount: number): String => {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD"
    }).format(amount);
};

export const formatPriceCalculation = (quantity: number, price: number) => {
    return (Number(quantity) * Number(price)).toFixed(2);
}
