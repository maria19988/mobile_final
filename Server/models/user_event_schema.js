const mongoose = require('mongoose');

const UserEventSchema = new mongoose.Schema({
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

module.exports = UserEventSchema;