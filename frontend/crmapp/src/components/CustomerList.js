// src/components/CustomerList.js

import React, { useState, useEffect } from 'react';

import axios from 'axios';

import ProductTypeFilter from './ProductTypeFilter';
import ProductListByType from './ProductListByType';
import './style.css';
import AddCustomerButton from "./AddCustomerButton";




const CustomerList = () => {

    const [customers, setCustomers] = useState([]);
    const [selectedProductType, setSelectedProductType] = useState(null);
    const [filterProductType, setFilterProductType] = useState('');

    const handleAddCustomer = (newCustomer) => {
        setCustomers([... customers, newCustomer]);
    };



    useEffect(() => {

        const fetchCustomers = async () => {

            try {

                const response = await axios.get('http://localhost:8080/api/v1/customers');

                setCustomers(response.data);

            } catch (error) {

                console.error('Error fetching customers:', error);

            }

        };



        fetchCustomers();

    }, []);



    const handleProductTypeClick = (productType) => {

        setSelectedProductType(productType);

        setFilterProductType(productType); // Set filter to selected product type

    };



    const handleFilterChange = (event) => {

        setFilterProductType(event.target.value);

        setSelectedProductType(null);

    };



    return (

        <div>

            <h2>Customer List</h2>



            <label>

                Filter by Product Type:{' '}

                <input

                    type="text"

                    value={filterProductType}

                    onChange={handleFilterChange}

                />

            </label>



            <ul>

                {customers.map((customer) => (

                    <li key={customer.id}>

                        <strong>Name:</strong> {customer.firstName} {customer.lastName} <br />

                        <strong>Email:</strong> {customer.email} <br />

                        <strong>Creation Date:</strong> {customer.creationDate} <br />

                        <strong>Products:</strong>

                        <ul>

                            {customer.products

                                .filter((product) =>

                                    product.productType.toLowerCase().includes(filterProductType.toLowerCase())

                                )

                                .map((product) => (

                                    <li key={product.id}>

                                        <strong>Product Name:</strong> {product.productName} <br />

                                        <strong>Product Type:</strong>{' '}

                                        <ProductTypeFilter

                                            productType={product.productType}

                                            onClick={handleProductTypeClick}

                                        />

                                        <br />

                                    </li>

                                ))}

                        </ul>

                        <hr />

                    </li>

                ))}

            </ul>



            {selectedProductType && (

                <ProductListByType

                    products={customers

                        .flatMap((customer) => customer.products)

                        .filter(

                            (product) => product.productType.toLowerCase() === selectedProductType.toLowerCase()

                        )}

                    selectedProductType={selectedProductType}

                />

            )}

            <AddCustomerButton onAddCustomer={handleAddCustomer} />

        </div>

    );

};



export default CustomerList;
