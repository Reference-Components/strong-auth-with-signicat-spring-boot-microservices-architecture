export type AuthUrlResponse = {
    endpointUrl: string
    request: string
}

export type UserData = {
    name?: string
    idToken?: string
    identityRawData?: string
}

export type UserDataResponse = Required<UserData>
