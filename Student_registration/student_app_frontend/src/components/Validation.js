import * as Yup from 'yup';

export function Validation () {
    const validationSchema = Yup.object().shape({
      name: Yup.string().required('Name is required'),
      father: Yup.string().required('Father name is required'),
      mobile: Yup.string()
      .required('Mobile number is required')
      .matches(/^\d{10}$/, 'Mobile number must be exactly 10 digits'),
      email: Yup.string()
        .required('Email is required')
        .email('Email is invalid'),
      password: Yup.string()
        .required('Password is required')
        .min(6, 'Password must be at least 6 characters')
        .max(20, 'Password must not exceed 20 characters'),
      dob: Yup.string()
      .matches(/^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d\d$/, 'Enter DOB as DD/MM/YYYY')

    });

    return validationSchema
}  