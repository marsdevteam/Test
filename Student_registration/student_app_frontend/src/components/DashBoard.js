import { useEffect, useState } from "react";
import { applications } from "../data/Data";

function DashBoard() {

    const[applicationList, setApplicationList] = useState("");

    useEffect(() => {
        applications()
            .then(async (res) => {
                if (res.ok) {
                    let data = await res.json();
                    setApplicationList(data);
                }
            })
    }, []);

    return <h1>DashBoard</h1>
}

export default DashBoard;