import { yupResolver } from '@hookform/resolvers/yup';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Avatar from '@mui/material/Avatar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import CssBaseline from '@mui/material/CssBaseline';
import Grid from '@mui/material/Grid';
import Link from '@mui/material/Link';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { Validation } from './Validation';
import { IconButton, InputAdornment } from '@mui/material';
import { Visibility, VisibilityOff } from '@mui/icons-material';

export default function Registration() {

    const [showPassword, setShowPassword] = React.useState(false);

    const handleTogglePasswordVisibility = () => {
      setShowPassword((prevShowPassword) => !prevShowPassword);
    };

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: yupResolver(Validation())
    });

    const navigate = useNavigate();

    const handleClick = () => {
        navigate("/");
    }

    const onSubmit = async data => {
        const name = data.name;
        const mobile = data.mobile;
        const email = data.email;
        const dob = data.dob;
        const fatherName = data.father;
        const password = data.password;
        let res = await fetch('http://localhost:8080/api/student/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name, mobile, email, dob, fatherName, password }),
        })
        if (res.ok) {
            let msg = await res.json();
            toast.success(msg.message);
        } else {
            toast.error("You are registered");
        }
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Registration
                </Typography>
                <Box component="form" noValidate sx={{ mt: 3 }}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                autoComplete="given-name"
                                name="name"
                                required
                                fullWidth
                                id="name"
                                label="Name"
                                autoFocus
                                {...register('name')}
                                error={errors.name ? true : false}
                            />
                            <Typography variant="inherit" color="textSecondary">
                                {errors.name?.message}
                            </Typography>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required

                                fullWidth
                                id="mobile"
                                label="Mobile"
                                name="mobile"
                                autoComplete="mobile"
                                {...register('mobile')}
                                error={errors.mobile ? true : false}
                            />
                            <Typography variant="inherit" color="textSecondary">
                                {errors.mobile?.message}
                            </Typography>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                onChange={(e) => {
                                    e.target.value = e.target.value.replace(/[^a-zA-Z0-9@.-]/g, '');
                                }}
                                fullWidth
                                id="email"
                                label="Email Address"
                                name="email"
                                autoComplete="email"
                                {...register('email')}
                                error={errors.email ? true : false}

                            />
                            <Typography variant="inherit" color="textSecondary">
                                {errors.email?.message}
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                id="dob"
                                label="DOB"
                                name="dob"
                                autoComplete="dob"
                                {...register('dob')}
                                error={errors.dob ? true : false}
                            />
                            <Typography variant="inherit" color="textSecondary">
                                {errors.dob?.message}
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                id="father"
                                label="Father's Name"
                                name="father"
                                autoComplete="father"
                                {...register('father')}
                                error={errors.father ? true : false}
                            />
                            <Typography variant="inherit" color="textSecondary">
                                {errors.father?.message}
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type={showPassword ? 'text' : 'password'}
                                id="password"
                                autoComplete="new-password"
                                {...register('password')}
                                error={errors.password ? true : false}
                                InputProps={{
                                    endAdornment: (
                                      <InputAdornment position="end">
                                        <IconButton onClick={handleTogglePasswordVisibility} edge="end">
                                          {showPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                      </InputAdornment>
                                    ),
                                  }}
                            />
                            <Typography variant="inherit" color="textSecondary">
                                {errors.password?.message}
                            </Typography>
                        </Grid>
                    </Grid>
                    <Button
                        onClick={handleSubmit(onSubmit)}
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Sign Up
                    </Button>
                    <Grid container justifyContent="flex-end">
                        <Grid item>
                            <Link className='link' variant="body2" onClick={handleClick} >
                                Already have an account? Sign in
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
}