package io.bnguyen.vocare;

public enum EnumVocareAPI
{
    
    SUCCESS("API_SUCCESS"),
    FAIL("API_FAIL"),
    // element transfer complete
    DONE("API_DONE"),
    // transaction complete
    END("API_END"),
    
    /* VALIDATE_ACCOUNT
     * Params: account name, password
     * Return: success or fail, end
     */
    VALIDATE_ACCOUNT("API_VALIDATE_ACCOUNT"),
     
    /* CREATE ACCOUNT
     * Params: account name, password
     * Return: failed items if failed, success or fail, end
     * Session will be logged in
     */
    CREATE_ACCOUNT("API_CREATE_ACCOUNT"),
    CREATE_ACCOUNT_ACCOUNTNAME_TAKEN("API_CREATE_ACCOUNT_ACCOUNTNAME_TAKEN"),
    CREATE_ACCOUNT_INVALIDEMAIL_TAKEN("API_CREATE_ACCOUNT_INVALIDEMAIL_TAKEN"),
    
    // Everything below needs an authenticated account
    
    /* CREATE USER
     * Params: username, first name, last name, phone, email
     * Return: failed items if failed, success or fail, end
     */
    CREATE_USER("API_CREATE_USER"),
    
    /* SET USER
     * Params: username
     * Return: SUCCESS or FAIL
     */
    SET_USER("API_SET_USER"),
    
    /* GET CHATS
     * Params: <none>
     * Return: chat-ids, who's in the chats
     * chat-id, userid1, userid2,... done, .... end
     */
    GET_CHATS("API_GET_CHATS"),
    
    
    /* SEND MESSAGE
     * Params: chatId, message content
     * Return: SUCCESS or FAIL, END
     */
    SEND_MESSAGE("API_SEND_MESSAGE"),
    
    /* UPDATE CLIENT
     * When server sends:
     * Params: chat-ids that need updating, end
     * 
     * When client sends:
     * Params: <none>
     * Returns: chat-ids that need updating, end
     */
    UPDATE_CLIENT("API_UPDATE_CLIENT"),
    
    /* GET CHAT MESSAGES
     * Params: chat-id
     * Return: user-id, content, done, user-id, content, done, ... end
     */
    GET_CHAT_MESSAGES("API_GET_CHAT_MESSAGES"),
    
    /* GET USER INFO
     * Params: user-id
     * Return: username, first name, last name 
     */
    GET_USER_INFO("API_GET_USER_INFO"),
    ;
    
    private String value;
    EnumVocareAPI(String value)
    {
        this.value = value;
    }
    
    public String toString()
    {
        return value;
    }
}
