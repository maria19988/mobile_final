const mongoose = require('mongoose');
const path = require('path');

const JobSchema = new mongoose.Schema({
    email: {
        type: String,
        required: true
    },
    title: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('UserJob', JobSchema);