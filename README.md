<h1> DocBlock </h1>

<p>Ever tried to contact a doctor but failed? Ever wanted to search the best doctor on the go? </p>
<p>We tried to solve this problem by creating DocBlock, an app where patient meets doctor! With highly sercure ```RSA-4096``` algorithm, all your personel detail are encrypted via secret personel <strong>public</strong> and <strong>private</strong> key. Not only this, all the conversation between patient and doctor are encrypted via RSA-4096. So, say <strong>bye</strong>, <strong>bye</strong> to Hackers!</p>

_________________

<h2> Idea </h2>

The idea is to create an app where the patient can get easily consult a doctor irrespective of distance between patient and doctor. So, we created DocBlock, a patient and doctor based totally customized app where both the patient as well as Doctor (where doctor's registration is validated by confirming from Indian Medical Association) can register and start consulting their patients.

Once the patient is registered, s/he can search the doctors either by name or by their speciality! And after they found the appropriate doctor, the patient shares his ```RSA public key``` with the doctor and the doctor does the same. These keys are generated at the time of generation and are stored on highly secure ```firebase``` server.

<h2> Working </h2>

The public key of patient is with doctor and the public key of doctor is with the patient. Whenever the patient wants to ask something from doctor, his messages are ```encrypted``` using that doctor's public key and when the message reaches doctor, it is decrypted by his (doctor's) private key.
_______

<h2> Contributors </h2>

[Arjun Bajpal](https://github.com/antailbaxt3r)<br>
[Mayank Garg](https://github.com/martinetmayank)
