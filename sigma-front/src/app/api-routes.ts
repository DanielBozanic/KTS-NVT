const API_BASE = 'http://localhost:8080';

export const API_GET_ALL_EMPLOYEES = API_BASE + '/users/getAllEmployees';
export const API_GET_EMPLOYEES_BY_CURRENT_PAGE =
  API_BASE + '/users/getEmployeesByCurrentPage';
export const API_GET_NUMBER_OF_ACTIVE_EMPLOYEE_RECORDS =
  API_BASE + '/users/getNumberOfActiveEmployeeRecords';
export const API_ADD_EMPLOYEE = API_BASE + '/users/addEmployee';
export const API_EDIT_EMPLOYEE = API_BASE + '/users/editEmployee';
export const API_DELETE_EMPLOYEE = API_BASE + '/users/deleteEmployee';
