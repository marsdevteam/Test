import { Container } from '@mui/material';
import { MaterialReactTable, useMaterialReactTable } from 'material-react-table';
import React, { useMemo } from 'react';

const ApplicationTable = (propes) => {

    const data = propes.data

    const columns = useMemo(
        () => [
            {
                accessorKey: 'university',
                header: 'University',
                size: 150,
            },
            {
                accessorKey: 'course',
                header: 'Course',
                size: 150,
            },
            {
                accessorKey: 'email',
                header: 'Approved',
                size: 150,
            }
        ],
        [],
    );

    const table = useMaterialReactTable({
        columns,
        data,
        enableFullScreenToggle: false,
    });
    return <Container component="main" maxWidth="md">
        <MaterialReactTable table={table} />
    </Container>
};

export default ApplicationTable;
