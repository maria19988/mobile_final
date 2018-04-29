const mongoose = require('mongoose');
const path = require('path');

const UserJobSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
	phone:{
		type: String,
		required:true
	}
});

module.exports = mongoose.model('Job', UserJobSchema);