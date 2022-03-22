class TokenManager {
    static instance: TokenManager
    private idToken: string | undefined

    constructor() {
        this.idToken = undefined
    }

    public setIdToken(idToken: string | undefined) {
        this.idToken = idToken
    }

    public getIdToken() {
        return this.idToken
    }

    static getInstance() {
        if (!TokenManager.instance) {
            TokenManager.instance = new TokenManager()
        }
        return this.instance
    }
}

export default TokenManager
