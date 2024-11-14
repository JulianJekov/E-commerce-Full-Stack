import React from 'react'
import { useCallback } from 'react';
import { useState } from 'react'


const SizeFilter = ({ size, hidleTitle}) => {

    const [appliedsize, setAppliedSize] = useState([]);
    const onClickDiv = useCallback((item) => {
        if (appliedsize.indexOf(item) > -1) {
            setAppliedSize(appliedsize?.filter(size => size !== item))
        } else {
            setAppliedSize([...appliedsize,item])
        }
    }, [appliedsize, setAppliedSize])


    return (
        <div className={`flex flex-col ${hidleTitle?'':'mb-4'}`}>
            {!hidleTitle && <p className='text-[16px] text-black mt-5 mb-5'>Size</p>}
            <div className='flex flex-wrap px-2'>
                {size?.map(item => {
                    return (
                        <div className='flex flex-col mr-2'>
                            <div className='w-[50px] h-8 text-center border rounded-lg mr-4 mb-4 pt-0.5 cursor-pointer 
                            hover:scale-105 bg-white border-gray-500 text-gray-500'
                            style={appliedsize.includes(item)?{background:'black', color:'white'}:{}}
                            onClick={() => onClickDiv(item)}>
                                {item}
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

export default SizeFilter