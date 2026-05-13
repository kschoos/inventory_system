import { Fragment } from "react";
import type { Tag } from "../types/Tag"; 

interface TagListProps {
    tags: Tag[];
    activeTags: number[];
    setActiveTags: React.Dispatch<React.SetStateAction<number[]>>;
}

export default function TagList ({tags, activeTags, setActiveTags} : TagListProps) {
    function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
        const tag = Number(e.target.id);
        const checked = e.target.checked;

        if (checked) {
            setActiveTags([...activeTags, tag]);
        } else {
            setActiveTags(activeTags.filter(x => x != tag));
        }
    }

    return <div className="btn-group-vertical" role="group" aria-label="Vertical button group">
        {
        tags.map((tag) => (
            <Fragment key={tag.id}>
                <input type="checkbox" className="btn-check" id={String(tag.id)} onChange={handleChange} autoComplete="off"/>
                <label className="btn btn-outline-primary" htmlFor={String(tag.id)}>{tag.name}</label>
            </Fragment>
        ))
        }
    </div>;
}