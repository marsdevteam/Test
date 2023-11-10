import React, { useEffect } from 'react';
import { applications } from '../data/Data';
import ApplicationTable from "./ApplicationTable";
import { useState } from 'react';

function DashBoard() {
    const [applicationList, seApplicationData] = useState(null);

    useEffect(() => {
        applications()
            .then(async (res) => {
                if (res.ok) {
                    let data = await res.json();
                    seApplicationData(data);
                }
            })
    }, []);

    if (applicationList == null) {
        return <h1> Loadding</h1>
    }
    return <>
        <ApplicationTable data={applicationList} />
    </>
}

export default DashBoard;