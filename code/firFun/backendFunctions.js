exports.copyToSomewhereElse = functions.database.ref('/collectivesTesting/{colName}/members/testVal').onWrite((event) => {
    // Grab the current value of what was written to the Realtime Database.
    const uName = event.data.val();
    const collectiveID = event.data.ref.parent.parent.val('collectiveId');
    console.log('Copying value to same area', event.params.pushId, uName);
    // You must return a Promise when performing asynchronous tasks inside a Functions such as
    // writing to the Firebase Realtime Database.
    // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
    //return event.data.ref.parent.child('copiedValue').set(nName);
    return admin.database().ref('/copiedValues').child('collectiveID').set(collectiveID);
});

exports.userAddedToCollective = functions.database.ref('/collectives/{collectiveName}/members/0').onWrite((event) => {
    
    const userEmail = event.data.val();
    const collectiveID = event.data.ref.parent.parent.val('collectiveId');
    const userID = admin.database().ref('/users')
    
    ));