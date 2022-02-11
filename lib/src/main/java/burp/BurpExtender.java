package burp;

import java.util.List;

public class BurpExtender implements IBurpExtender, IHttpListener {

    private static String JWT;
    private IExtensionHelpers helpers;

    static {
        JWT = "MOCK-JWT-TO-ADD-TO-EACH-REQUEST";
    }

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {

        helpers = callbacks.getHelpers();

        callbacks.setExtensionName("Burp-JWT-Injector");
        callbacks.registerHttpListener(this);

    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {

        System.out.println("making request: " + new String(messageInfo.getRequest()));

        // we only want to add the auth headers to outbound requests
        if (messageIsRequest) {

            IRequestInfo requestInfo = helpers.analyzeRequest(messageInfo);
            List<String> headers = requestInfo.getHeaders();

            // make all request modifications here
            addAuthHeader(headers);

            // fashion a new request (that includes all the modifications made)
            String request = new String(messageInfo.getRequest());
            String body = request.substring(requestInfo.getBodyOffset());
            byte[] newRequest = helpers.buildHttpMessage(headers, body.getBytes());

            messageInfo.setRequest(newRequest);

        }

    }

    private void addAuthHeader(List<String> headers) {
        String authHeader = String.format("Authorization: Bearer %s", JWT);
        headers.add(authHeader);
    }
}
