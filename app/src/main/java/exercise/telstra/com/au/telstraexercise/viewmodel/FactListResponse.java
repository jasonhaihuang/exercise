package exercise.telstra.com.au.telstraexercise.viewmodel;

import exercise.telstra.com.au.telstraexercise.model.FactList;

/**
 * This class encapsulate the response of the getFactList response.
 *
 * If the request is successful, the factList will contain the data;
 * If the request failed, the errorMessage will contain related error information.
 */
public class FactListResponse {
    public FactList factList;
    public String errorMessage;

    public FactListResponse(FactList list, String msg){
        factList = list;
        errorMessage = msg;
    }
}
