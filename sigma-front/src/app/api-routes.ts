const API_BASE = 'http://localhost:8080';

export const API_GET_ALL_EMPLOYEES = API_BASE + '/users/getAllEmployees';
export const API_GET_EMPLOYEES_BY_CURRENT_PAGE =
  API_BASE + '/users/getEmployeesByCurrentPage';
export const API_GET_NUMBER_OF_ACTIVE_EMPLOYEE_RECORDS =
  API_BASE + '/users/getNumberOfActiveEmployeeRecords';
export const API_ADD_EMPLOYEE = API_BASE + '/users/addEmployee';
export const API_EDIT_EMPLOYEE = API_BASE + '/users/editEmployee';
export const API_DELETE_EMPLOYEE = API_BASE + '/users/deleteEmployee';

export const API_GET_ZONE = API_BASE + '/zones/';
export const API_GET_ALL_ZONES = API_BASE + '/zones/getAll';
export const API_GET_TABLES_FOR_ZONE = API_BASE + '/zones/tables/';
export const API_GET_ITEMS_FOR_ORDER = API_BASE + '/orders/items/'
export const API_GET_DRINKS_FOR_ORDER = API_BASE + '/orders/drinks/'
export const API_GET_FOODS_FOR_ORDER = API_BASE + '/orders/foods/'
