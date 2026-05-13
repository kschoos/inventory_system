import type { Tag } from "./Tag"
import type { Location } from "./Location"

export interface Item {
    id: number;
    name: string;
    count: number;
    imageUrl: string;
    tags: Tag[];
    location: Location;
}