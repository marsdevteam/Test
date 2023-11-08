import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import * as React from 'react';

export default function Footer() {
  return (

    <Box
      component="footer"
      sx={{
        py: 3,
        px: 2,
        mt: 'auto',
        backgroundColor: (theme) =>
          theme.palette.mode === 'light'
            ? theme.palette.grey[200]
            : theme.palette.grey[800],
        bottom: '0',
        position: 'fixed',
        width: '-webkit-fill-available',


      }}
    >
      <Container maxWidth="sm">
        <Typography variant="body1"
          sx={{
            display: 'flex',
            justifyContent: 'center'
          }}>
          Footer is here
        </Typography>
      </Container>
    </Box>
  );
}