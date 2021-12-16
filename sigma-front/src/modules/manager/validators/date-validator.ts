export function DateValidator(date: Date): { [key: string]: boolean } | null {
  const currentDate = new Date();
  if (new Date(date) < currentDate) {
    return { dateInvalid: true };
  } else {
    return null;
  }
}
