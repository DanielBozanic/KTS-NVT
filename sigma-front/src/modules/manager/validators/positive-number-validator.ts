export function PositiveNumberValidator(
  n: number
): { [key: string]: boolean } | null {
  if (n < 0) {
    return { positiveNumberInvalid: true };
  } else {
    return null;
  }
}
