import sendHttpRequest from "../../../http_call/HttpRequest";
import CustomerUrlConfig from "../../../service_url/CustomerUrlConfig";
 async function showByPage(pageNo, pageSize) {
    let customerResponse = await sendHttpRequest(
        CustomerUrlConfig.CUSTOMER_SHOWBYPAGE + `?pageNo=${pageNo}&pageSize=${pageSize}`
    );

   // console.log(customerResponse);
    let arr = customerResponse.json.dto.content
    let i = 0
    arr.forEach((element) => {
        element.token = customerResponse.json.tokens[i]
        i++;
      });
    return arr

}

export default showByPage