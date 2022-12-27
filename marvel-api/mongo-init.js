db.createUser(
    {
        user: "usr_marvel",
        pwd: "pwd_marvel",
        roles: [
            {
                role: "readWrite",
                db: "marvel"
            }
        ]
    }
);