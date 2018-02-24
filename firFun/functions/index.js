// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

/*exports.addMessage = functions.https.onRequest((req, res) => {
    // Grab the text parameter.
    const original = req.query.text;
// Push the new message into the Realtime Database using the Firebase Admin SDK.
return admin.database().ref('/messages').push({original: original}).then((snapshot) => {
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    return res.redirect(303, snapshot.ref);
});
});*/

/*exports.addMessage = functions.https.onRequest((req, res) => {
    // Grab the text parameter.
    const original = req.query.text;
// Push the new message into the Realtime Database using the Firebase Admin SDK.
    return admin.database().ref('/Test').push({original: original}).then((snapshot) => {
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    return res.redirect(303, snapshot.ref);
});
});


exports.makeUppercase = functions.database.ref('/Test/{somestupidshite}/listenHere/original').onWrite((event) => {
 
const original = event.data.val();
console.log('Uppercasing', event.params.pushId, original);
const uppercase = original.toUpperCase();
return event.data.ref.parent.child('uppercase').set(uppercase);
});*/

/*exports.addCollectivetoCreatorAccount = functions.database.ref('/collectives/{colName}/members/0').onWrite((event) => {
    const uName = event.data.val();
    const colID = event.params.colName;
    var path = '/collectives/' + colID + '/creator';
    return admin.database().ref(path).once('value').then((snap) => {        
        const creator = snap.val();
        path = '/users/' + creator + '/';
        return admin.database().ref(path).child('myCollectives').set(colID);        
    });
}); */

exports.addCollectivetoUserAccount = functions.database.ref('/collectives/{colName}/members/').onWrite((event) => {
    const uName = event.data.val();
    const member = event.data.child('0').val(); //only sets the collective to the user at array[0];
    const colID = event.params.colName;
    var userID = null;
    return admin.database().ref('/users').once('value').then((snap) => {
        snap.forEach(function(childSnapshot){
            var childSnapEmail = childSnapshot.child('email').val();
            if(childSnapEmail===member){
               userID = childSnapshot.key;
           }
        }); 
        return admin.database().ref('/users/' + userID).child('myCollectives').set(colID);        
    });
}); 

/*exports.addCollectiveIDtoMemberAccounts = functions.database.ref('/collectives/{colName}/members/').onWrite((event) => {
    const colID = event.params.colName; //needed to access array at col Event
    var i = 0;
    var NoUsersExceptition = {};
    var NoUserFoundExceptition = {};
    return admin.database().ref('/users').once('value').then((snap) => {
         if(snap.hasChildren()){ //have to make sure it's not triggered on a null set
            while(event.data.child(i.toString()).exists()){ //while the next child in the array isn't null
                var found = false;
                    snap.forEach(function(childSnapshot){
                        var childSnapEmail = childSnapshot.child('email').val();
                        var member = event.data.child(i.toString()).val(); //get value at array[i]
                           if (childSnapEmail===member){
                              var userID = childSnapshot.key;
                              found = true;
                              return admin.database().ref('/users/' + userID).child('myCollectives').set(colID);
                              //return true; //break out of the forEach when found
                         }
                    });
                    if(!found)
                        throw NoUserFoundExceptition;
                i++; //increment the counter
            }       
         }
         else{
             throw NoUsersExceptition;
         }
         return; 
     });
});*/

exports.addCollectiveIDtoMemberAccountsUpgrade = functions.database.ref('/collectives/{colName}/members').onUpdate((event) => { //only runs when data is updated
    const colID = event.params.colName; //needed to access array at col Event
    var i = 0;
    var NoUsersExceptition = {};
    var NoUserFoundExceptition = {};
    return admin.database().ref('/users').once('value').then((snap) => {
         if(snap.hasChildren()){ //have to make sure it's not triggered on a null set
            while(event.data.child(i.toString()).exists() === true){ //while the next child in the array isn't null
                var found = false;
                    snap.forEach(function(childSnapshot){
                        var member = event.data.child(i.toString()).child('usrEmail').val(); //get value at col/members[i]/usrEmail
                        var childSnapEmail = childSnapshot.child('email').val(); //get value at users[i]
                           if (childSnapEmail===member){
                              var userID = childSnapshot.key;
                              var array = [];
                              found = true;
                              var userColRef = admin.database().ref('/users/' + userID + '/myCollectives'); //we need to obtain the array of the user's collectives
                              var j = 0;
                              return userColRef.once("value").then(function(colListSnapshot){
                                    while(colListSnapshot.child(j.toString()).exists() === true){
                                        array.push(colListSnapshot.child(j.toString()).val());
                                        j++;
                                    }
                                    array.push(colID);
                                    admin.database().ref('/users/' + userID).child('myCollectives').set(array);
                                    return true; //break out of the forEach
                                    });
                         }
                         else
                             console.log('Not found at users[' + i.toString() + '].');

                    });
                    if(!found)
                        throw NoUserFoundExceptition;
                i++; //increment the counter
            }       
         }
         else{
             throw NoUsersExceptition;
         }
         return admin.database().ref('/collectives/' + colID + '/members/newMembers').remove(); //remove the new member set
     });
});

exports.arrayTest = functions.database.ref('/testingStuff/ListenArray').onWrite((event) => {
    
    var array = [];
    var i = 0; 
    var ref = admin.database().ref('testingStuff/TestArray');
    return ref.once("value").then(function(snapshot){
        while(snapshot.child(i.toString()).exists()){
            array.push(snapshot.child(i.toString()).val());
            i++;
        }
        return admin.database().ref('/testingStuff').child('NewArray').set(array); 
        });
    });

exports.countUsers = functions.database.ref('/users').onWrite((event) => {
    var count = 0;

    return admin.database().ref('/users').once('value').then(function(snapshot){
        snapshot.forEach(function(childSnapshot){
            count++;
        });
         return admin.database().ref('/test').child('countOfUsers').set(count);
    });
});