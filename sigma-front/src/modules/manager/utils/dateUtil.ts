import * as moment from 'moment';

export function stringToDate(data: Date): moment.Moment {
  return moment(data, 'YYYY-MM-DDThh:mm:ss');
}

export function increaseMonth(date: moment.Moment): moment.Moment {
  if (date.month() == 12) {
    return moment(date).set('month', 1).add(1, 'y');
  }
  return moment(date).add(1, 'M');
}
