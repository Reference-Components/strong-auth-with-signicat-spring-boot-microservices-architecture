class TokenManager {
    static instance: TokenManager
    private idToken: string | undefined
    private exp: number | undefined

    constructor() {
        this.idToken = undefined
        this.exp = undefined
    }

    public setIdToken(idToken: string | undefined) {
        this.idToken = idToken
    }

    public getIdToken() {
        return this.idToken
    }

    public getExp() {
        return this.exp
    }

    public setExp(exp: number) {
        this.exp = exp
    }

    static getInstance() {
        if (!TokenManager.instance) {
            TokenManager.instance = new TokenManager()
        }
        return this.instance
    }
}

export default TokenManager
