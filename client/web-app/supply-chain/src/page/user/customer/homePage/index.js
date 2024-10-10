import React, {useState} from 'react';
import './style.scss'

const HomePage = () => {
    const [count, setCount] = useState(0);

    function incrementCount() {
        return count + 1;
    }

    function handleClick() {
        // setCount((a) => { return a + 1});
        // setCount((a) => { return a + 1});
        // setCount((a) => { return a + 1});
        setCount(incrementCount);
        setCount(incrementCount);
        setCount(incrementCount);
    }

    return (
        <div>
            HomePage
            {/*<button onClick={handleClick} style={{width:"100px"}}>{count}</button>*/}
        </div>
    );
}

export default HomePage;