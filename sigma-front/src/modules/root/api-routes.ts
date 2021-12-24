const API_BASE = 'http://localhost:8080';

export const API_GET_ALL_EMPLOYEES = API_BASE + '/users/getAllEmployees';
export const API_GET_EMPLOYEES_BY_CURRENT_PAGE =
  API_BASE + '/users/getEmployeesByCurrentPage';
export const API_GET_NUMBER_OF_ACTIVE_EMPLOYEE_RECORDS =
  API_BASE + '/users/getNumberOfActiveEmployeeRecords';
export const API_ADD_EMPLOYEE = API_BASE + '/users/addEmployee';
export const API_EDIT_EMPLOYEE = API_BASE + '/users/editEmployee';
export const API_DELETE_EMPLOYEE = API_BASE + '/users/deleteEmployee';
export const API_GET_EMPLOYEE = API_BASE + '/users/';

export const API_GET_ALL_ITEMS = API_BASE + '/items';
export const API_GET_ITEMS_BY_FOOD_TYPE =
  API_BASE + '/items/getItemsByFoodType/';
export const API_GET_ITEMS_BY_SEARCH_TERM =
  API_BASE + '/items/getItemsBySearchTerm/';
export const API_GET_ITEMS_BY_CURRENT_PAGE =
  API_BASE + '/items/getItemsByCurrentPage';
export const API_CREATE_NEW_ITEM = API_BASE + '/items/createNewItem';

export const API_CREATE_NEW_MENU = API_BASE + '/menu/addMenu';
export const API_DELETE_MENU = API_BASE + '/menu/';
export const API_GET_ITEMS_IN_MENU_BY_CURRENT_PAGE =
  API_BASE + '/menu/getItemsInMenuByCurrentPage/';
export const API_GET_ITEMS_IN_MENU =
  API_BASE + '/item-in-menu/getAll/';
export const API_GET_ITEMS_IN_MENU_BY_SEARCH_TERM =
  API_BASE + '/item-in-menu/getItemsBySearchTerm/';
export const API_GET_ITEMS_IN_MENU_BY_FOOD_TYPE =
  API_BASE + '/item-in-menu/getItemsByFoodType/';
export const API_GET_NUMBER_OF_ACTIVE_ITEM_IN_MENU_RECORDS_BY_MENU_ID =
  API_BASE + '/menu/getNumberOfActiveItemInMenuRecordsByMenuId/';
export const API_ADD_ITEM_IN_MENU = API_BASE + '/menu/addItemToMenu';
export const API_REMOVE_ITEM_FROM_MENU = API_BASE + '/menu/removeItemFromMenu';
export const API_GET_ACTIVE_NON_EXPIRED_MENUS =
  API_BASE + '/menu/getActiveNonExpiredMenus';

export const API_GET_ZONE = API_BASE + '/zones/';
export const API_GET_ALL_ZONES = API_BASE + '/zones/getAll';
export const API_GET_TABLES_FOR_ZONE = API_BASE + '/zones/tables/';
export const API_GET_ITEMS_FOR_ORDER = API_BASE + '/orders/items/';
export const API_GET_DRINKS_FOR_ORDER = API_BASE + '/orders/drinks/';
export const API_GET_FOODS_FOR_ORDER = API_BASE + '/orders/foods/';
export const API_CHANGE_TABLE_STATE = API_BASE + '/tables/';
export const API_CHANGE_ITEM_STATE = API_BASE + '/item-in-order/';
export const API_GET_ORDER = API_BASE + '/orders/';
export const API_GET_ITEM_IN_ORDER = API_BASE + '/item-in-order/';
export const API_GET_ALL_FOOD_ORDERS = API_BASE + '/orders/allFoodOrders/';
export const API_POST_ORDER = API_BASE + '/orders/';
export const API_DELETE_ORDER = API_BASE + '/orders/';
export const API_ADD_ITEM_TO_ORDER = API_BASE + '/orders/';
export const API_REMOVE_ITEM_FROM_ORDER = API_BASE + '/orders/';
