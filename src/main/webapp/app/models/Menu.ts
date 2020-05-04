/*
 *Menu Model.
 */
export class Menu {
  /** Generate Menu */
  constructor(
    public displayName: string,
    public disabled: boolean,
    public iconName: string,
    public route: string,
    public children: Menu[]
  ) {}
}
