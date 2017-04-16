package io.bnguyen.vocare;
public class VocareAPI
{
    
    public static final String SUCCESS = "API_SUCCESS";
    public static final String FAIL = "API_FAIL";
    // element transfer complete
    public static final String DONE = "API_DONE";
    // transaction complete
    public static final String END = "API_END";
    
    // needs authentication
    public static final String NEED_AUTH = "API_NEED_AUTH";
    
    // acknowledge
    public static final String ACK = "API_ACK";
    
    /* LOGIN ACCOUNT
     * Params: account name, password
     * Return: success or fail, end
     */
    public static final String LOGIN_ACCOUNT = "API_LOGIN_ACCOUNT";
    
    /* LOGOUT ACCOUNT
     * Logs out of the account
     * Return: end
     */
    public static final String LOGOUT_ACCOUNT = "API_LOGOUT_ACCOUNT";
     
    /* CREATE ACCOUNT
     * Params: account name, password
     * Return: failed items if failed, success or fail, end
     * Session will be logged in
     */
    public static final String CREATE_ACCOUNT = "API_CREATE_ACCOUNT";
    public static final String CREATE_ACCOUNT_ACCOUNTNAME_TAKEN = "API_CREATE_ACCOUNT_ACCOUNTNAME_TAKEN";
    public static final String CREATE_ACCOUNT_INVALIDEMAIL= "API_CREATE_ACCOUNT_INVALIDEMAIL";
    
    // Everything below needs an authenticated account
    
    /* CREATE USER
     * Params: username, first name, last name, phone, email
     * Return: failed items if failed, success or fail, end
     */
    public static final String CREATE_USER = "API_CREATE_USER";
    public static final String CREATE_USER_USERNAME_TAKEN = "API_CREATE_USER_USERNAME_TAKEN";
    
    /* SET USER
     * Params: username
     * Return: SUCCESS or FAIL
     */
    public static final String SET_USER = "API_SET_USER";
   
    /* CREATE CHAT
     * Params: username1, username2, .... done, end
     * Return: chat-id
     */
    public static final String CREATE_CHAT = "API_CREATE_CHAT";
    
    /* GET CHATS
     * Params: <none>
     * Return: chat-ids, who's in the chats
     * chat-id, userid1, userid2,... done, .... end
     */
    public static final String GET_CHATS = "API_GET_CHATS";
    
    
    /* SEND MESSAGE
     * Params: chatId, message content
     * Return: SUCCESS or FAIL, END
     */
    public static final String SEND_MESSAGE = "API_SEND_MESSAGE";
    
    /* UPDATE CLIENT
     * When server sends:
     * Params: chat-ids that need updating, end
     * 
     * When client sends:
     * Params: <none>
     * Returns: chat-ids that need updating, end
     */
    public static final String UPDATE_CLIENT = "API_UPDATE_CLIENT";
    
    /* GET CHAT MESSAGES
     * Params: chat-id
     * Return: user-id, content, done, user-id, content, done, ... end
     */
    public static final String GET_CHAT_MESSAGES = "API_GET_CHAT_MESSAGES";
    
    /* GET USER INFO
     * Params: user-id
     * Return: username, first name, last name 
     */
    public static final String GET_USER_INFO = "API_GET_USER_INFO";
}
