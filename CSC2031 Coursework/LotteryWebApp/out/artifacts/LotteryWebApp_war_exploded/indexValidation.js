$().ready(function(){ //define and initialise the function

    $.validator.addMethod('strongPassword', function (value, element){
        return this.optional(element) ||
            (value.match(/[a-z]/) && value.match(/[a-z]/) && value.match(/[0-9]/));
    }, "Please enter a strong password");

    $.validator.addMethod('phonePattern', function (value, element){
        return this.optional(element) ||
            /[0-9]{2}-[0-9]{4}-[0-9]{7}/.test(value);
    }, "Please use the right phone format");

    $(" form[name = 'CreateUser']").validate({
        rules: {
            //define rules using elements name attribution from index.jsp
            firstname: "required",
            lastname: "required",
            email: {
                required: true,
                email: true
            },
            phone: {
                required: true,
                phonePattern: true
            },
            username: "required",
            password: {
                required: true,
                minlength:8,
                maxLength: 15,
                strongPassword: true
            }

        },

            messages: {
                firstname: "Please enter your firstname",
                lastname: "Please enter your lastname",
                email: {
                    required: "Email address required",
                    email: "Email address must be valid"
                },
                phone: {
                    required: "Phone number required",
                    phonePattern: "Phone number must be in the format xx-xxxx-xxxxxxx"
                },
                username: {
                    required: "Username required"
                },
                password: {
                    required: "Password Required",
                    minlength: "Password has to be at least 8 characters long",
                    maxlength: "Password has to be at most 15 characters long",
                    strongPassword: "Password must contain 8-15 characters which include one uppercase, one lowercase and one digit"
                },
            },
    })
});