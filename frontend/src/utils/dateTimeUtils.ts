export const millisToTimeStr = (expiryInMillis: number | undefined) => {
    if (expiryInMillis) {
        return new Date(expiryInMillis).toString()
    }
    return '-'
}
