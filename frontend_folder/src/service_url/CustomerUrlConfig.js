import HOST_URL from "./AppUrlConfig";

const CUSTOMER_SERVICE_URL = `${HOST_URL}/customers`

const CUSTOMER_SERVICE_GETALL = `${CUSTOMER_SERVICE_URL}/all`
const CUSTOMER_SEARCH = `${CUSTOMER_SERVICE_URL}/search`
// const CUSTOMER_UPDATE = `${CUSTOMER_SERVICE_URL}/update`
// const CUSTOMER_DELETE = `${CUSTOMER_SERVICE_URL}/delete`
const CUSTOMER_SHOWBYPAGE = `${CUSTOMER_SERVICE_URL}/page`

export default { CUSTOMER_SERVICE_URL, CUSTOMER_SEARCH,CUSTOMER_SHOWBYPAGE,CUSTOMER_SERVICE_GETALL}