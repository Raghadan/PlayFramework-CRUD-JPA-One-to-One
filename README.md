# PlayFramework-CRUD-JPA-One-to-One
Using Play Framework V3, we are creating an application which manages the student data with Citizen ID using One to One Mapping in the postgres db with the default configuration in Asynchronous mode.

The mapped By attribute enables to communicatate in bidirectional mode and it is defined in the non-owning side(parent table) of the table.
The @JoinColumn attribute is used in Child table( or the owning side)
