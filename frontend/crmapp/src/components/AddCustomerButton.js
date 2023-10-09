// src/components/AddCustomerButton.js

import React, { useState } from 'react';

import './style.css';
import axios from 'axios';



const AddCustomerButton = ({ onAddCustomer }) => {

    const [showModal, setShowModal] = useState(false);

    const [newCustomerData, setNewCustomerData] = useState({

        email: '',

        firstname: '',

        lastname: '',

    });



    const handleAddCustomer = async () => {

        try {

            // Make a POST request to add a new customer

            const response = await axios.post('/api/v1/customers', newCustomerData);



            // Assuming your API returns the newly created customer

            const newCustomer = response.data;



            // Close the modal

            setShowModal(false);



            // Reset the form data

            setNewCustomerData({ email: '', firstname: '', lastname: '' });



            // Pass the new customer data to the parent component

            onAddCustomer(newCustomer);



            console.log('New customer added:', newCustomer);

        } catch (error) {

            console.error('Error adding new customer:', error.message);

        }

    };



    const handleInputChange = (e) => {

        const { name, value } = e.target;

        setNewCustomerData({ ...newCustomerData, [name]: value });

    };



    const openModal = () => setShowModal(true);

    const closeModal = () => setShowModal(false);



    return (
        <>
            <button onClick={openModal}>Add New Customer</button>



            {/* Modal for adding a new customer */}
            {showModal && (
                <div className="modal-overlay">
                    <div className="modal">
                        <h3>Add New Customer</h3>
                        <form onSubmit={(e) => e.preventDefault()}>
                            <label>Email:</label>
                            <input
                                type="email"
                                name="email"
                                value={newCustomerData.email}
                                onChange={handleInputChange}
                            />



                            <label>First Name:</label>
                            <input
                                type="text"
                                name="firstname"
                                value={newCustomerData.firstname}
                                onChange={handleInputChange}
                            />



                            <label>Last Name:</label>
                            <input
                                type="text"
                                name="lastname"
                                value={newCustomerData.lastname}
                                onChange={handleInputChange}
                            />



                            <button type="button" onClick={handleAddCustomer}>
                                Add Customer
                            </button>
                            <button type="button" onClick={closeModal}>
                                Cancel
                            </button>
                        </form>
                    </div>
                </div>
            )}
        </>
    );
};



export default AddCustomerButton;