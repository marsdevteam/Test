import { Box, Button, Container, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { courses, universities } from '../data/Data.js';
import { Card } from 'react-bootstrap-v5';
import { toast } from 'react-toastify';

function ApplyForm() {

    const [universityList, setUniversityData] = useState(null);
    const [coursesList, setCoursesList] = useState(null);
    const [course, setCourse] = React.useState('');
    const [university, setUniversity] = React.useState('');    
    const [comments, setComments] = React.useState('');

    useEffect(() => {
        universities()
            .then(async (res) => {
                if (res.ok) {
                    let data = await res.json();
                    setUniversityData(data);
                }
            })
    }, []);

    useEffect(() => {
        courses()
            .then(async (res) => {
                if (res.ok) {
                    let data = await res.json();
                    setCoursesList(data);
                }
            })
    }, []);

    const handleCourse = (event) => {
        setCourse(event.target.value);
    };

    const handleUniversity = (event) => {
        setUniversity(event.target.value);
    };

    const handleSubmit = async () => {
        let res = await fetch('http://localhost:8080/api/student/apply', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': JSON.parse(localStorage.getItem('currentUser')).access_token
          },
          body: JSON.stringify({ course, university, comments}),
        })

        if (res.ok) {
          let data = await res.json();
          console.log(data);
          toast.success(data.message);
        } else {
            toast.error("You have already applied");
        }
    }
    
    return <Container component="main" maxWidth="xs">
        <Box sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
        }} >
            <Card variant="outlined" style={{ width: '-webkit-fill-available', display: 'grid', gap: '5%' }}>
                <FormControl fullWidth>
                    <InputLabel id="UniversityId">Select University</InputLabel>
                    <Select
                        fullWidth
                        labelId="universityLable"
                        id="university"
                        value={university}
                        label="Select University"
                        onChange={handleUniversity}
                    >
                        {universityList && universityList.length > 0 ? (
                            universityList.map((university, index) => (
                                <MenuItem key={index} value={university}>
                                    {university}
                                </MenuItem>
                            ))
                        ) : (
                            <MenuItem value="">
                                No universities available
                            </MenuItem>
                        )}
                    </Select>
                </FormControl>
                <FormControl fullWidth>
                    <InputLabel id="courseId">Select Course</InputLabel>
                    <Select
                        labelId="dcourseLable"
                        id="course"
                        value={course}
                        label="Select Course"
                        onChange={handleCourse}
                    >
                        {coursesList && coursesList.length > 0 ? (
                            coursesList.map((course, index) => (
                                <MenuItem key={index} value={course}>
                                    {course}
                                </MenuItem>
                            ))
                        ) : (
                            <MenuItem value="">
                                No Courses available
                            </MenuItem>
                        )}
                    </Select>
                </FormControl>
                <TextField
                    id="outlined-multiline-static"
                    label="Comments"
                    multiline
                    rows={4}
                    onChange={(e) => setComments(e.target.value)}
                />
                <Button
                    onClick={handleSubmit}
                    fullWidth
                    variant="contained"
                    sx={{ mt: 1, mb: 2 }}
                >
                    Submit
                </Button>
            </Card>
        </Box>
    </Container>
}

export default ApplyForm