import { Fragment } from "react";

export default function TagList ({tags, activeTags, setActiveTags}) {
    function handleChange(e) {
        const tag = e.target.id;
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
                <input type="checkbox" className="btn-check" id={tag.id} onChange={handleChange} autoComplete="off"/>
                <label className="btn btn-outline-primary" htmlFor={tag.id}>{tag.name}</label>
            </Fragment>
        ))
        }
    </div>;
}