import React from 'react'
import Navigation from '../../components/Navigation/Navigation'
import { Outlet } from 'react-router-dom'
import BackGroundImage from '../../assets/imgs/bg-1.png'
import { useSelector } from 'react-redux';
import Spinner from '../../components/Spinner/Spinner';

const AuthenticationWrapper = () => {

    const isLoading = useSelector((state)=> state?.commonState?.loading);
    return (
        <div className=''>
            <Navigation variant="auth"/>
            <div className='flex justify-center min-h-screen'>
                <div className='w-[60%]  hidden bg-cover lg:block lg:w-2/6 md:inline py-2'>
                    <img src={BackGroundImage} className='bg-cover  rounded-2xl w-full bg-center' alt='shoppingimage'/>
                </div>
                <div>
                    <Outlet />
                </div>
                {isLoading && <Spinner />}
            </div>
        </div>
    )
}

export default AuthenticationWrapper